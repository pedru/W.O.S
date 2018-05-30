from django.contrib import admin
from exams.models import Exam, Question, Lecture, Answer


@admin.register(Lecture)
class LectureAdmin(admin.ModelAdmin):
    pass


@admin.register(Exam)
class ExamAdmin(admin.ModelAdmin):
    list_display = ['id','date','lecture','question_count']
    pass


@admin.register(Question)
class QuestionAdmin(admin.ModelAdmin):
    pass


@admin.register(Answer)
class AnswerAdmin(admin.ModelAdmin):
    list_display = ['question', 'text', 'owner']
    