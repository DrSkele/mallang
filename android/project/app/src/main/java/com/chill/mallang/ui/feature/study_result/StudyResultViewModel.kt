package com.chill.mallang.ui.feature.study_result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyResultViewModel
    @Inject
    constructor(
        savedStandHandle: SavedStateHandle,
    ) : ViewModel() {
        private val _studyResultState = MutableStateFlow<StudyResultState>(StudyResultState.Loading)
        val studyResultState = _studyResultState.asStateFlow()

        init {
            gradeQuiz(savedStandHandle.get<Int>("studyId") ?: -1)
        }

        private fun gradeQuiz(studyId: Int) {
            viewModelScope.launch {
                // 퀴즈 결과 불러오기 api 실행
                _studyResultState.value =
                    StudyResultState.Success(
                        quizTitle = "빈칸을 채워 주세요",
                        quizScript = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 __할만한 성장을 이루었다.",
                        wordList =
                            arrayListOf(
                                "괄목" to "눈을 비비고 볼 정도로 매우 놀라다.",
                                "상대" to "서로 마주 대하다.",
                                "과장" to "사실보다 지나치게 불려서 말하거나 표현하다.",
                                "시기" to "때나 경우.",
                            ),
                        result = false,
                        systemAnswer = 1,
                    )
            }
        }
    }