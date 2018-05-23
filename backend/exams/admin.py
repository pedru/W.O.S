from django.contrib import admin
from exams.models import Exam, Question, Lecture


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
