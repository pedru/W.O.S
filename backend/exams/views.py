from django.shortcuts import get_object_or_404
from rest_framework import viewsets, generics
from rest_framework.authentication import TokenAuthentication
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import IsAuthenticated, IsAuthenticatedOrReadOnly
from rest_framework.response import Response
import logging

from backend.permissions import IsOwnerOrReadOnly
from exams.models import Exam, Question, Lecture, Answer
from exams.serializers import ExamListSerializer, QuestionListSerializer, LectureDetailSerializer, LectureSerializer, \
    ExamDetailSerializer, ExamCreateSerializer, AnswerListSerializer

logger = logging.getLogger(__name__)


class ExamViewSet(viewsets.ModelViewSet):
    queryset = Exam.objects.all()
    serializer_class = ExamListSerializer
    permission_classes = (IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly)
    authentication_classes = (TokenAuthentication,)

    def get_serializer_class(self):
        print(self.action)
        if self.action == 'retrieve':
            return ExamDetailSerializer
        if self.action == 'create':
            return ExamCreateSerializer
        else:
            return ExamListSerializer

    def perform_create(self, serializer):
        serializer.save(owner=self.request.user, lecture_id=self.request.data['lecture_id'])


class QuestionViewSet(viewsets.ModelViewSet):
    queryset = Question.objects.all()
    permission_classes = (IsAuthenticated,)
    authentication_classes = (TokenAuthentication,)

    def get_serializer_class(self):
        if self.action == 'retrieve':
            return QuestionDetailSerializer
        elif self.action == 'create':
            return QuestionCreateSerializer
        else:
            return QuestionListSerializer


class AnswerViewSet(viewsets.ModelViewSet):
    queryset = Answer.objects.all()
    serializer_class = AnswerListSerializer


class LectureViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Lecture.objects.all()

    def get_serializer_class(self):
        if (self.action == 'retrieve'):
            return LectureDetailSerializer
        else:
            return LectureSerializer


class ExamSearch(generics.ListAPIView):
    serializer_class = ExamListSerializer

    def get_queryset(self):
        """
        This view should return a list of all the purchases
        for the currently authenticated user.
        """
        needle = self.kwargs['needle']
        return Exam.objects.all()


@api_view(['POST'])
@permission_classes((IsAuthenticated,))
def subscribe(request):
    if 'exam_id' not in request.data:
        return Response({'detail': 'Missing parameter exam_id'}, 422)

    exam_id = request.data['exam_id']
    try:
        exam_id = int(exam_id)
    except ValueError:
        return Response({'detail': 'exam_id has to be of integer type'}, 400)

    exam = get_object_or_404(Exam, pk=exam_id)
    exam.subscribed.add(request.user)
    return Response({'detail': 'Subscribed to Exam {}'.format(exam)}, 201)


@api_view(['POST'])
@permission_classes((IsAuthenticated,))
def unsubscribe(request):
    """
    Unsubscribe from an exam.
    """
    if 'exam_id' not in request.data:
        return Response({'detail': 'Missing parameter exam_id'}, 422)

    exam_id = request.data['exam_id']
    try:
        exam_id = int(exam_id)
    except ValueError:
        return Response({'detail': 'exam_id has to be of integer type'}, 400)

    exam = get_object_or_404(Exam, pk=exam_id)
    exam.subscribed.remove(request.user)
    return Response({'detail': 'Unsubscribed from Exam {}'.format(exam)}, 200)


@api_view(['POST'])
@permission_classes((IsAuthenticated,))
def upvote(request):
    if 'id' not in request.data:
        return Response({'detail': 'Missing parameter id'}, 422)
    question_id = request.data['id']
    question = get_object_or_404(Question, pk=question_id)
    try:
        QuestionVoting.objects.get(user=request.user, question=question)
        return Response({'detail': 'Question "{}" is already upvoted'.format(question)}, 409)
    except QuestionVoting.DoesNotExist:
        QuestionVoting(user=request.user, question=question, weight=1).save()
        return Response({'detail': 'Upvoted question "{}"'.format(question)}, 201)


