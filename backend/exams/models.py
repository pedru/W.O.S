from django.contrib.auth.models import AbstractUser, User
from django.db import models
from studies.models import Study


class Lecture(models.Model):
    """
    Represents a lecture
    """
    name = models.CharField(max_length=150)
    studies = models.ManyToManyField(Study, related_name='lectures')

    def __str__(self):
        return self.name


class Exam(models.Model):
    """
    Stores an exam that occurs on a specific date and belongs
    to a user.
    """
    name_max_length = 200  # TODO: what is the maximum length
    lecture = models.ForeignKey(Lecture, on_delete=models.CASCADE, related_name='exams')
    date = models.DateField()
    owner = models.ForeignKey(User, on_delete=models.CASCADE)
    subscribed = models.ManyToManyField(User, related_name="exams", blank=True)
    created = models.DateField(auto_now_add=True)

    def __str__(self):
        return "{}".format(self.lecture)

    @property
    def question_count(self):
        return self.questions.count()


class AdditionalInformation(models.Model):
    """
    Stores additional information for a specific exam
    """
    description = models.TextField()
    link = models.URLField(verbose_name="Additional Link with external information")
    exam = models.ForeignKey(Exam, on_delete=models.CASCADE)


class AdditionalInformationRating(models.Model):
    """
    Additional information can be rated by users
    """
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    information = models.ForeignKey(AdditionalInformation, on_delete=models.CASCADE)
    weight = models.IntegerField(default=1)


class Question(models.Model):
    """
    A single question of an exam
    """
    exam = models.ForeignKey(Exam, on_delete=models.CASCADE, related_name='questions')
    question = models.TextField()  # TODO: Formatierungsmöglichkeiten?
    user = models.ForeignKey(User, on_delete=models.CASCADE)

    def __str__(self):
        return self.question


class Answer(models.Model):
    """
    Possible answer to a question
    """
    question = models.ForeignKey(Question, models.CASCADE, related_name='answers')
    text = models.TextField()
    owner = models.ForeignKey(User, on_delete=models.CASCADE)

    def __str__(self):
        return self.text


class Rating(models.Model):
    """
    Rating of an answer by a user
    """
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    answer = models.ForeignKey(Answer, on_delete=models.CASCADE)
    weight = models.IntegerField(default=1, help_text="The weight of the vote, can be positive or negative.")
    important = models.IntegerField(default=0, help_text="Stores wether the question was asked at the exam.")


# TODO: needs specification
meeting_status = (('active', 'Active'), ('canceled', 'Canceled'))


class Meeting(models.Model):
    """
    Meeting to prepare for an exam
    """
    exam = models.ForeignKey(Exam, on_delete=models.CASCADE)
    location = models.CharField(max_length=150)
    date = models.DateField()
    status = models.IntegerField(choices=meeting_status)


class MeetingAttendance(models.Model):
    """
    Stores which users will attend a meeting
    """
    meeting = models.ForeignKey(Meeting, on_delete=models.CASCADE)
    user = models.ForeignKey(User, on_delete=models.CASCADE)
