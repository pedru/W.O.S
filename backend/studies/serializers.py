from rest_framework import serializers

from exams.serializers import LectureSerializer
from studies.models import Study


class StudyListSerializer(serializers.ModelSerializer):

    class Meta:
        model = Study
        fields = ('id', 'name')

class StudyDetailSerializer(serializers.ModelSerializer):
    lectures = LectureSerializer(many=True)

    class Meta:
        model = Study
        fields = StudyListSerializer.Meta.fields + ('lectures',)