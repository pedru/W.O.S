from django.test import TestCase, SimpleTestCase


# Create your tests here.
from studies.models import Study


class StudyTestCase(SimpleTestCase):

    def test_str(self):
        study = Study()
        study.name = "simplename"
        self.assertEqual(str(study), study.name)


