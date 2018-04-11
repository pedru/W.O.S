from rest_framework import serializers

from exams.models import Exam, Question, ExamDate
from studies.serializers import StudyListSerializer

class QuestionListSerializer(serializers.ModelSerializer):

    class Meta:
        model = Question
        fields = ('id', 'question', 'user')


class ExamDateListSerializer(serializers.ModelSerializer):

    # TODO: add subscribers
    # subscribers =

    questions = QuestionListSerializer(many=True)

    class Meta:
        model = ExamDate
        fields = ('id','date','time_start', 'time_end', 'questions')


class ExamListSerializer(serializers.ModelSerializer):
    study = StudyListSerializer()
    exam_dates = ExamDateListSerializer(many=True)

    class Meta:
        model = Exam
        fields = ('id', 'lecture', 'study', 'owner', 'created', 'exam_dates')



