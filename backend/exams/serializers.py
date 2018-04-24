from rest_framework import serializers

from exams.models import Exam, Question, Lecture


class QuestionListSerializer(serializers.ModelSerializer):
    class Meta:
        model = Question
        fields = ('id', 'question', 'user')


class LectureSerializer(serializers.ModelSerializer):
    class Meta:
        model = Lecture
        fields = ('id', 'name', 'exams')


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


class LectureDetailSerializer(LectureSerializer):
    exams = ExamListSerializer(many=True)

    class Meta:
        model = Lecture
        fields = LectureSerializer.Meta.fields + ('exams',)

