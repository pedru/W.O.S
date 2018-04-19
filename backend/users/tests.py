from django.test import TestCase
from rest_framework.test import APIClient

from users.apps import UsersConfig
from django.apps import apps


class UsersConfigTest(TestCase):
    def test_apps(self):
        self.assertEqual(UsersConfig.name, 'users')
        self.assertEqual(apps.get_app_config('users').name, 'users')

class UserGetTokenApiTest(TestCase):
    def setUp(self):
        self.client = APIClient()

    def test_get_token(self):
        response = self.client.get("http://localhost:8000/api/user/gettoken")
        self.assertEquals(response.body.user, "anon1")
        self.assertEquals(len(response.body.user), 20)


class UserCheckTokenApiTest(TestCase):
    # TODO: Check how Django REST handles authentication API
    # TODO API: Check and verify token
    pass