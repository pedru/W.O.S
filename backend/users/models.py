from django.db import models

# Create your models here.


class BackendUser(models.Model):
    """
    Empty user object
    """
    token = models.CharField(max_length=32, help_text="Token is the authentication for a user")
