# Generated by Django 2.0.5 on 2018-06-06 12:07

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('exams', '0008_questionvoting'),
    ]

    operations = [
        migrations.RenameField(
            model_name='answer',
            old_name='owner',
            new_name='user',
        ),
    ]
