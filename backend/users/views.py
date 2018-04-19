from django.contrib.auth.models import User
from rest_framework.authtoken.models import Token
from rest_framework.decorators import api_view
from rest_framework.response import Response



#@throttle_classes([OncePerDayUserThrottle])
@api_view(['GET'])
def create_user(request):
    user_count = User.objects.count()
    user = User.objects.create(username="anon{}".format(user_count))
    user.set_unusable_password()
    token = Token.objects.create(user=user)
    return Response({"user": user.username,"token": token.key})