from datetime import date

from django.contrib.auth.models import User
from django.test import TestCase, TransactionTestCase, SimpleTestCase
from mixer.backend.django import mixer
from rest_framework.test import APIClient, force_authenticate

from django.apps import apps
from exams.apps import ExamsConfig
from exams.models import Exam, Lecture, Question, Answer, QuestionVoting
from studies.models import Study


class UserTestCase(TransactionTestCase):
    fixtures = ['test_user']

    def setUp(self):
        self.client = APIClient()
        self.user = User.objects.get(pk=1)
        self.client.force_authenticate()


class ExamsConfigTest(SimpleTestCase):
    def test_apps(self):
        self.assertEquals(ExamsConfig.name, 'exams')
        self.assertEquals(apps.get_app_config('exams').name, 'exams')


class ExamTestCase(TransactionTestCase):
    def setUp(self):
        lecture = mixer.blend(Lecture, name="Foolecture")
        study = mixer.blend(Study, name="Foostudy")
        self.test_exam = mixer.blend(Exam, lecture=lecture, study=study, date=date(2018, 6, 26))

    def test_str(self):
        # Exam string identifier should consist of a name
        self.assertEquals(str(self.test_exam), "Foolecture")

    def test_question_count(self):
        # Start with zero questions
        self.assertEquals(self.test_exam.question_count, 0)

        # Test creating two questions
        mixer.blend(Question, exam=self.test_exam)
        mixer.blend(Question, exam=self.test_exam)
        self.assertEquals(self.test_exam.question_count, 2)

        # Add a third question
        mixer.blend(Question, exam=self.test_exam)
        self.assertEquals(self.test_exam.question_count, 3)


class LectureTestCase(SimpleTestCase):
    def setUp(self):
        self.test_lecture_name = 'Softwaretechnologie'

    def test_str(self):
        test_lecture = Lecture(name=self.test_lecture_name)
        self.assertEquals(str(test_lecture), self.test_lecture_name)


class QuestionTestCase(SimpleTestCase):
    def test_str(self):
        question = Question(question='Foo?')
        self.assertEquals(str(question), question.question)

class QuestionPropertiesTestCase(TransactionTestCase):

    def test_score(self):
        q = mixer.blend(Question)
        mixer.blend(QuestionVoting, weight=1, question=q)

        self.assertEquals(q.score, 1)
        mixer.blend(QuestionVoting, weight=1, question=q)
        self.assertEquals(q.score, 2)
        mixer.blend(QuestionVoting, weight=-1, question=q)
        self.assertEquals(q.score, 1)

class AnswerTestCase(SimpleTestCase):
    def test_str(self):
        answer = Answer(text='Bar')
        self.assertEquals(str(answer), answer.text)


class CreateExamApiTest(TransactionTestCase):
    create_url = '/api/exams/'

    def setUp(self):
        self.client = APIClient()
        self.test_lecture_id = 5
        self.lecture = mixer.blend(Lecture, pk=self.test_lecture_id)
        self.exam_date = '2017-06-07'

    def test_create(self):

        payload = {'date': self.exam_date, 'owner_id': 1, 'lecture_id': self.test_lecture_id}
        response = self.client.post(self.create_url, payload)

        self.assertEquals(response.status_code, 200)
        try:
            Exam.objects.filter(date=self.exam_date, lecture=self.lecture)
        except Exam.DoesNotExist:
            self.fail("Exam was not created.")


class RetrieveExamListApiTest(TransactionTestCase):
    def setUp(self):
        self.client = APIClient()
        self.test_lecture_name = 'Softwaretechnologie'

    def test_serial(self):
        test_lecture = mixer.blend(Lecture, name=self.test_lecture_name)
        mixer.blend(Exam, lecture=test_lecture)
        response = self.client.get("/api/exams/")
        self.assertEquals(len(response.data), 1)
        self.assertEquals(response.data[0]['lecture']['name'], self.test_lecture_name)


class RetrieveExamApiTest(TestCase):
    # TODO API: Retrieval of a single exam, including a list of all questions and answers
    pass


class SearchExamApiTest(TestCase):
    # TODO API: Users should be able to search for exams inside their study using autocomplete
    pass


class SubscriptionTestCase(UserTestCase):
    subscribe_action_url = '/api/exams/subscribe'

    def setUp(self):
        self.user = User.objects.get(pk=1)
        self.client = APIClient()
        self.client.force_authenticate(self.user)

    def subscribe_action(self, payload: dict = None):
        return self.client.post(self.subscribe_action_url, payload, format='json')


class SubscribeToExamDateApiTest(SubscriptionTestCase):

    def test_subscribe(self):
        # Omitting parameter
        response = self.subscribe_action()
        self.assertEquals(response.status_code, 422)

        # Wrong type of parameter
        response = self.subscribe_action({'exam_id': 'foo'})
        self.assertEquals(response.status_code, 400)

        # Non-existing exam
        response = self.subscribe_action({'exam_id': 1})
        self.assertEquals(response.status_code, 404)

        # Successful subscription
        mixer.blend(Exam, id=1)
        response = self.subscribe_action({'exam_id': '1'})
        self.assertEquals(response.status_code, 201)


class UnsubscribeFromExamTestCase(SubscriptionTestCase):
    subscribe_action_url = '/api/exams/unsubscribe'

    def test_unsubscribe(self):
        # Create an exam with a user subscribed to it
        exam = mixer.blend(Exam, id=1)
        self.user.exams.add(exam)
        self.assertEquals(exam.subscribed.count(), 1)

        # Unsubscribe from exam
        response = self.subscribe_action({'exam_id': '1'})
        self.assertEquals(exam.subscribed.count(), 0)
        self.assertEquals(response.status_code, 200)

        # Check if wrong argument type causes error
        response = self.subscribe_action({'exam_id': 'foo'})
        self.assertEquals(response.status_code, 400)

        # Check if omitting parameters causes error
        response = self.subscribe_action({})
        self.assertEquals(response.status_code, 422)


class CreateQuestionApiTest(UserTestCase):
    question_api_url = '/api/questions/'
    exam_id = 1
    test_question = 'Test Question?'

    def setUp(self):
        # Create an exam to attach the question to
        mixer.blend(Exam, id=self.exam_id)
        super().setUp()

    def test_create_question(self):
        # Create a new question via the API

        response = self.client.post(self.question_api_url, {'exam_id': self.exam_id, 'question': self.test_question, 'user':self.request.user})
        self.assertEquals(response.status_code, 201)

        # Check if the question was created
        try:
            created_question = Question.objects.get(question=self.test_question)
            self.assertEquals(created_question.exam_id, self.exam_id)
        except Question.DoesNotExist:
            self.fail('Test question was not saved to database')

    def test_error_non_existent_exam(self):
        non_existing_id = 99999
        response = self.client.post(self.question_api_url, {'exam_id': non_existing_id, 'question': self.test_question})
        self.assertEquals(response.status_code, 404, "Non-existing exam should raise 404")

    def test_error_missing_parameter(self):
        response = self.client.post(self.question_api_url, {'exam_id': self.exam_id})
        self.assertEquals(response.status_code, 422, "Missing parameter should raise 422")

        response = self.client.post(self.question_api_url, {'question': 'Foo?'})
        self.assertEquals(response.status_code, 422, "Missing parameter should raise 422")


class CreateAnswerApiTest(UserTestCase):
    answer_api_url = '/api/answers'

    def setUp(self):
        # Create a question to post an answer to
        self.question_id = 1
        self.test_question = mixer.blend(Question, id=self.question_id)

    def test_create_answer(self):
        # Post an answer to the question
        response = self.client.post(self.answer_api_url,
                                    {'answer': 'The answer to this test is 42.', 'question_id': self.question_id})
        self.assertEquals(response.status_code, 200)
        self.assertEquals(self.test_question.answers.count(), 1)

        # Post a second answer to the question
        response = self.client.post(self.answer_api_url,
                                    {'answer': 'So you brought a towel, thank god!', 'question_id': self.question_id})
        self.assertEquals(response.status_code, 200)
        self.assertEquals(self.test_question.answers.count(), 2)

    def test_post_no_parameters(self):
        # Post with no parameters to get an error (422)
        response = self.client.post(self.answer_api_url, {})
        self.assertEquals(response.status_code, 422)
        self.assertEquals(self.test_question.answers.count(), 0)

    def test_post_unknown_parameters(self):
        # Post with wrongly named parameters to get an error
        response = self.client.post(self.answer_api_url, {'foo': '42'})
        self.assertEquals(response.status_code, 422)
        self.assertEquals(self.test_question.answers.count(), 0)

    def test_post_empty_answer(self):
        # Post an empty answer consisting only of whitespace to get an error
        response = self.client.post(self.answer_api_url, {'answer': '', 'question_id': self.question_id})
        self.assertEquals(response.status_code, 422)
        self.assertEquals(self.test_question.answers.count(), 0)

        # Post a whitespace only answer to get an error
        response = self.client.post(self.answer_api_url, {'answer': 42 * ' ', 'question_id': self.question_id})
        self.assertEquals(response.status_code, 422)
        self.assertEquals(self.test_question.answers.count(), 0)


class RateQuestionApiTest(TestCase):
    # TODO API: Users can rate questions
    pass


class RateAnswerApiTest(TestCase):
    # TODO API: Users are able to rate answers
    pass

class UpvoteQuestionApiTest(UserTestCase):
    upvote_url = '/api/question/upvote'
    def setUp(self):
        self.user = User.objects.get(pk=1)
        self.client = APIClient()
        self.client.force_authenticate(self.user)

    def test_upvote(self):
        mixer.blend(Question, id=1)
        response = self.client.post(self.upvote_url,{'id':1})
        self.assertEqual(response.status_code, 201)
        response = self.client.post(self.upvote_url, {'id': 1})
        self.assertEqual(response.status_code, 409)
        response = self.client.post(self.upvote_url,{'id_exam':1})
        self.assertEqual(response.status_code, 422)
        response = self.client.post(self.upvote_url,{'id':2})
        self.assertEqual(response.status_code, 404)
        response = self.client.post(self.upvote_url, {})
        self.assertEqual(response.status_code, 422)


