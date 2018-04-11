from django.contrib import admin

from studies.models import Study


@admin.register(Study)
class StudyAdmin(admin.ModelAdmin):

    pass
