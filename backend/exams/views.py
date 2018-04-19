from rest_framework import viewsets, generics

from exams.models import Exam, Question
from exams.serializers import ExamListSerializer, QuestionListSerializer


class ExamViewSet(viewsets.ModelViewSet):
    queryset = Exam.objects.all()
    serializer_class = ExamListSerializer


class QuestionViewSet(viewsets.ModelViewSet):
    queryset = Question.objects.all()
    serializer_class = QuestionListSerializer

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

