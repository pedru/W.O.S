from django.contrib import admin
from exams.models import Exam, Question, Lecture


@admin.register(Lecture)
class LectureAdmin(admin.ModelAdmin):
    pass

@admin.register(Exam)
class ExamAdmin(admin.ModelAdmin):
    pass

@admin.register(Question)
class QuestionAdmin(admin.ModelAdmin):
    pass
