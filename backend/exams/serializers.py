from rest_framework import serializers

from exams.models import Exam, Question, Lecture
from studies.serializers import StudyListSerializer

class QuestionListSerializer(serializers.ModelSerializer):

    class Meta:
        model = Question
        fields = ('id', 'question', 'user')


class LectureSerialize(serializers.ModelSerializer):
    class Meta:
        model = Lecture

class ExamListSerializer(serializers.ModelSerializer):
    study = StudyListSerializer()
    class Meta:
        model = Exam
        fields = ('id', 'lecture', 'study', 'date', 'owner', 'created')



