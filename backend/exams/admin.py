from django.contrib import admin
from exams.models import Exam

@admin.register(Exam)
class ExamAdmin(admin.ModelAdmin):
    pass


