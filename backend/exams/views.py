from rest_framework import viewsets, generics

from exams.models import Exam, Question, Lecture
from exams.serializers import ExamListSerializer, QuestionListSerializer, LectureDetailSerializer, LectureSerializer, \
    ExamDetailSerializer


class ExamViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Exam.objects.all()
    serializer_class = ExamListSerializer

    def get_serializer_class(self):
        if(self.action == 'retrieve'):
            return ExamDetailSerializer
        else:
            return ExamListSerializer


class QuestionViewSet(viewsets.ModelViewSet):
    queryset = Question.objects.all()
    serializer_class = QuestionListSerializer


class LectureViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Lecture.objects.all()

    def get_serializer_class(self):
        if (self.action == 'retrieve'):
            return LectureDetailSerializer
        else:
            return LectureSerializer


class ExamSearch(generics.ListAPIView):
    serializer_class = ExamListSerializer

    def get_queryset(self):
        """
        This view should return a list of all the purchases
        for the currently authenticated user.
        """
        needle = self.kwargs['needle']
        print(needle)
        return Exam.objects.all()
