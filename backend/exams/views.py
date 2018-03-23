from rest_framework import viewsets

from exams.models import Exam
from exams.serializers import ExamListSerializer


class ExamListViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Exam.objects.all()
    serializer_class = ExamListSerializer

