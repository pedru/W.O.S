from rest_framework.serializers import ModelSerializer

from users.models import BackendUser


class BackendUserSerializer(ModelSerializer):
    class Meta:
        model = BackendUser