"""backend URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, reverse_lazy
from django.conf.urls import url, include
from django.views.generic import RedirectView
from rest_framework import routers

from exams.views import ExamSearch, ExamViewSet, LectureViewSet
from studies.views import StudyListViewSet
from users.views import create_user, user_detail

router = routers.DefaultRouter()
router.register(r'exams', ExamViewSet)
router.register(r'studies', StudyListViewSet)
router.register(r'exams', ExamViewSet)
router.register(r'lecture', LectureViewSet)


admin.site.site_header = 'LeaBackend'

urlpatterns = [
    url(r'^api-auth/', include('rest_framework.urls')),
    url(r'^api/', include(router.urls)),
    path('admin/', admin.site.urls),
    url('^$', RedirectView.as_view(url=reverse_lazy('admin:index'))),
    url('^api/user/token', create_user),
    url('^api/user/detail', user_detail),
    url('^api/exams/search/(?P<needle>.+)/$', ExamSearch.as_view()),
    url('^api/user/token', create_user)
]