from django.test import TestCase

from users.apps import UsersConfig
from django.apps import apps


class UsersConfigTest(TestCase):
    def test_apps(self):
        self.assertEqual(UsersConfig.name, 'users')
        self.assertEqual(apps.get_app_config('users').name, 'users')

class UserGetTokenApiTest(TestCase):
    # TODO API: User gets a new token
    pass


class UserCheckTokenApiTest(TestCase):
    # TODO: Check how Django REST handles authentication API
    # TODO API: Check and verify token
    pass