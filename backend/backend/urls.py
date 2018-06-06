from django.contrib import admin
from django.urls import path
from django.conf.urls import url, include
from rest_framework import routers
from rest_framework.documentation import include_docs_urls

from exams.views import ExamSearch, ExamViewSet, LectureViewSet, subscribe, unsubscribe, QuestionViewSet, AnswerViewSet, upvote
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

urlpatterns = [
    url(r'^api-auth/', include('rest_framework.urls')),
    url(r'^api/', include(router.urls)),
    url(r'^docs/', include_docs_urls(title='Lea Rest-API', public=False)),
    path('admin/', admin.site.urls),
    #url('^$', RedirectView.as_view(url=reverse_lazy('admin:index'))),
    url('^api/user/token', create_user),
    url('^api/user/detail', user_detail),
    url('^api/exams/search/(?P<needle>.+)/$', ExamSearch.as_view()),
    url('^api/user/token', create_user),
    url('^api/exams/subscribe', subscribe),
    url('^api/exams/unsubscribe', unsubscribe),
    url('^api/question/upvote', upvote)
]
