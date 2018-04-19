from rest_framework import serializers

from exams.models import Exam, Question, Lecture
from studies.serializers import StudyListSerializer


class QuestionListSerializer(serializers.ModelSerializer):
    class Meta:
        model = Question
        fields = ('id', 'question', 'user')


class LectureSerializer(serializers.ModelSerializer):
    class Meta:
        model = Lecture
        fields = ('id', 'name')


class ExamListSerializer(serializers.ModelSerializer):
    lecture = LectureSerializer()
    questions = QuestionListSerializer(many=True)

    class Meta:
        model = Exam
        fields = ('id', 'lecture', 'date', 'owner', 'created', 'questions')