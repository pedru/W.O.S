from datetime import date

from django.contrib.auth.models import User
from django.test import TestCase, TransactionTestCase, SimpleTestCase
from mixer.backend.django import mixer
from rest_framework.test import APIClient, force_authenticate

from django.apps import apps
from exams.apps import ExamsConfig
from exams.models import Exam, Lecture, Question, Answer
from studies.models import Study


class UserTestCase(TransactionTestCase):
    fixtures = ['test_user']


class ExamsConfigTest(SimpleTestCase):
    def test_apps(self):
        self.assertEqual(ExamsConfig.name, 'exams')
        self.assertEqual(apps.get_app_config('exams').name, 'exams')


class ExamTestCase(TransactionTestCase):

    def setUp(self):
        lecture = mixer.blend(Lecture, name="Foolecture")
        study = mixer.blend(Study, name="Foostudy")
        self.test_exam = mixer.blend(Exam, lecture=lecture, study=study, date=date(2018, 6, 26))

    def test_str(self):
        self.assertEquals(str(self.test_exam), "Foolecture")

    def test_question_count(self):
        self.assertEquals(self.test_exam.question_count, 0)
        mixer.blend(Question, exam=self.test_exam)
        mixer.blend(Question, exam=self.test_exam)
        self.assertEquals(self.test_exam.question_count, 2)
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


class AnswerTestCase(SimpleTestCase):
    def test_str(self):
        answer = Answer(text='Bar')
        self.assertEquals(str(answer), answer.text)


class CreateExamApiTest(TransactionTestCase):
    pass
    # TODO API: User should be able to create a new exam


class RetrieveExamListApiTest(TransactionTestCase):

    def setUp(self):
        self.client = APIClient()
        self.test_lecture_name = 'Softwaretechnologie'

    def test_serial(self):
        test_lecture = mixer.blend(Lecture, name=self.test_lecture_name)
        mixer.blend(Exam, lecture=test_lecture)
        response = self.client.get("http://localhost:8000/api/exams/")
        self.assertEquals(len(response.data), 1)
        self.assertEquals(response.data[0]['lecture']['name'], self.test_lecture_name)


class RetrieveExamApiTest(TestCase):
    # TODO API: Retrieval of a single exam, including a list of all questions and answers
    pass


class SearchExamApiTest(TestCase):
    # TODO API: Users should be able to search for exams inside their study using autocomplete
    pass


class SubscribeToExamDateApiTest(UserTestCase):

    def setUp(self):
        self.user = User.objects.get(pk=1)
        self.client = APIClient()

    def subscribe_to_exam(self, payload: dict = None):
        return self.client.post('/api/exams/subscribe', payload, format='json')

    def test_subscribe(self):
        self.client.force_authenticate(self.user)

        # Omitting parameter
        response = self.subscribe_to_exam()
        self.assertEquals(response.status_code, 422)

        # Wrong type of parameter
        response = self.subscribe_to_exam({'exam_id':'foo'})
        self.assertEquals(response.status_code, 400)

        # Non-existing exam
        response = self.subscribe_to_exam({'exam_id': 1})
        self.assertEquals(response.status_code, 404)

        # Successful subscription
        mixer.blend(Exam, id=1)
        response = self.subscribe_to_exam({'exam_id': '1'})
        self.assertEquals(response.status_code, 201)


class CreateQuestionApiTest(TestCase):
    # TODO API: Users should be able to ask/add questions to an exam
    pass


class CreateAnswerApiTest(TestCase):
    # TODO API: User creates a new answer to a question
    pass


class RateQuestionApiTest(TestCase):
    # TODO API: Users can rate questions
    pass


class RateAnswerApiTest(TestCase):
    # TODO API: Users are able to rate answers
    pass
