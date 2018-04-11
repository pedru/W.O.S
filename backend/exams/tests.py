from django.test import TestCase, TransactionTestCase, SimpleTestCase
from mixer.backend.django import mixer
from rest_framework.test import APIClient

from exams.models import Exam, Lecture


class LectureTestCase(SimpleTestCase):

    def test_str(self):
        lecture_name = "Foobar"
        l = mixer.blend(Lecture, name=lecture_name)
        self.assertEquals(str(l), lecture_name)

class CreateExamApiTest(TestCase):
    # TODO API: User should be able to create a new exam
    pass


class RetrieveExamListApiTest(TransactionTestCase):
    def setup(self):
        self.client = APIClient()

    def test_serial(self):
        mixer.blend(Exam, name="stuff")
        response = self.client.get("http://localhost:8000/api/exams/")
        self.assertEquals(len(response.data), 1)
        self.assertEquals(response.data[0]['name'], "stuff")

class RetrieveExamApiTest(TestCase):
    # TODO API: Retrieval of a single exam, including a list of all questions and answers
    pass


class SearchExamApiTest(TestCase):
    # TODO API: Users should be able to search for exams inside their study using autocomplete
    pass


class SubscribeToExamDateApiTest(TestCase):
    # TODO API: Support the action of a user to subscribe to a specific exam date
    pass


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