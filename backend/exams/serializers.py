from rest_framework import serializers

from exams.models import Exam


class ExamListSerializer(serializers.ModelSerializer):

    class Meta:
        model = Exam
        fields = ('id', 'lecture')

