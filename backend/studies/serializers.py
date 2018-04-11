from rest_framework import serializers

from studies.models import Study


class StudyListSerializer(serializers.ModelSerializer):

    class Meta:
        model = Study
        fields = ('id', 'name')

