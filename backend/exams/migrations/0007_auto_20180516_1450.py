# Generated by Django 2.0.5 on 2018-05-16 14:50

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('exams', '0006_answer_question'),
    ]

    operations = [
        migrations.RenameField(
            model_name='answer',
            old_name='user',
            new_name='owner',
        ),
    ]
