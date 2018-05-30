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
from rest_framework.documentation import include_docs_urls
from rest_framework_swagger.views import get_swagger_view

from exams.views import ExamSearch, ExamViewSet, LectureViewSet, subscribe, unsubscribe, AnswerViewSet, QuestionViewSet
from studies.views import StudyListViewSet
from users.views import create_user, user_detail

router = routers.DefaultRouter()
router.register(r'exams', ExamViewSet)
router.register(r'studies', StudyListViewSet)
router.register(r'exams', ExamViewSet)
router.register(r'lecture', LectureViewSet)
router.register(r'questions', QuestionViewSet)
router.register(r'answers', AnswerViewSet)

admin.site.site_header = 'LeaBackend'


schema_view = get_swagger_view(title='LeaRestBackend-API')

urlpatterns = [
    url(r'^api-auth/', include('rest_framework.urls')),
    url(r'^api/', include(router.urls)),
    url(r'^docs/', include_docs_urls(title='Lea Rest-API', public=False)),
    url(r'^$', schema_view),
    path('admin/', admin.site.urls),
    #url('^$', RedirectView.as_view(url=reverse_lazy('admin:index'))),
    url('^api/user/token', create_user),
    url('^api/user/detail', user_detail),
    url('^api/exams/search/(?P<needle>.+)/$', ExamSearch.as_view()),
    url('^api/user/token', create_user),
    url('^api/exams/subscribe', subscribe),
    url('^api/exams/unsubscribe', unsubscribe)
]