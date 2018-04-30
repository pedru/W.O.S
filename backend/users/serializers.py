from django.contrib.auth.models import User
from rest_framework.serializers import ModelSerializer

from exams.serializers import ExamListSerializer


class UserSerializer(ModelSerializer):
    class Meta:
        model = User
        fields = ('id','username')


class UserDetailSerializer(UserSerializer):
    exams = ExamListSerializer(many=True)
    class Meta:
        model = User
        fields = UserSerializer.Meta.fields + ('exams',)