from django.db import models

class Study(models.Model):
    name = models.CharField(verbose_name="Name of the Study", max_length=255)

    def __str__(self):
        return self.name
