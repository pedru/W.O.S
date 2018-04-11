from django.contrib import admin
from exams.models import Exam, ExamDate


@admin.register(Exam)
class ExamAdmin(admin.ModelAdmin):
    pass


@admin.register(ExamDate)
class ExamDateAdmin(admin.ModelAdmin):
    pass

