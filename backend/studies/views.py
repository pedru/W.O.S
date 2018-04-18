from rest_framework import viewsets

from studies.models import Study
from studies.serializers import StudyListSerializer


class StudyListViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Study.objects.all()
    serializer_class = StudyListSerializer