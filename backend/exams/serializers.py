from django.contrib.auth.models import User
from rest_framework import serializers

from exams.models import Exam, Question, Lecture


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

    class Meta:
        model = Exam
        fields = ('id', 'lecture', 'date', 'owner', 'created', 'question_count')


class ExamDetailSerializer(ExamListSerializer):
    questions = QuestionListSerializer(many=True)

    class Meta:
        model = Exam
        fields = ExamListSerializer.Meta.fields + ('questions',)


class ExamCreateSerializer(serializers.ModelSerializer):
    # owner = serializers.ReadOnlyField()
    class Meta:
        model = Exam
        fields = ['lecture_id', 'date', 'owner_id']

    def create(self, validated_data):
        lecture = Lecture.objects.get(pk=validated_data['lecture_id'])
        owner_id = validated_data['owner'].id
        created_model = self.Meta.model.objects.create(lecture=lecture, date=validated_data['date'], owner_id=owner_id)
        created_model.save()
        return created_model


class LectureDetailSerializer(LectureSerializer):
    exams = ExamListSerializer(many=True)

    class Meta:
        model = Lecture
        fields = LectureSerializer.Meta.fields + ('exams',)
