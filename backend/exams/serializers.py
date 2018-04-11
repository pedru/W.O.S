from rest_framework import serializers

from exams.models import Exam
from studies.serializers import StudyListSerializer


class ExamListSerializer(serializers.ModelSerializer):

    study = StudyListSerializer()

    class Meta:
        model = Exam
        fields = ('id', 'lecture', 'study', 'owner', 'created')

