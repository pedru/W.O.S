from rest_framework import viewsets, generics

from exams.models import Exam
from exams.serializers import ExamListSerializer
from studies.models import Study
from studies.serializers import StudyListSerializer


class ExamListViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Exam.objects.all()
    serializer_class = ExamListSerializer


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

