from django.test import TestCase, SimpleTestCase

# Create your tests here.
from studies.models import Study

from studies.apps import StudiesConfig
from django.apps import apps


class StudiesConfigTest(TestCase):
    def test_apps(self):
        self.assertEqual(StudiesConfig.name, 'studies')
        self.assertEqual(apps.get_app_config('studies').name, 'studies')


class StudyTestCase(SimpleTestCase):
    def test_str(self):
        study = Study(name="Example study")
        self.assertEqual(str(study), study.name)
