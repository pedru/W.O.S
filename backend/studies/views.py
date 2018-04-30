from rest_framework import viewsets

from studies.models import Study
from studies.serializers import StudyListSerializer, StudyDetailSerializer


class StudyListViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Study.objects.all()

    def get_serializer_class(self):
        if self.action == 'retrieve':
            return StudyDetailSerializer
        else:
            return StudyListSerializer
