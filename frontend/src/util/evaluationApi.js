import {ref} from 'vue';
import axios from 'axios';

// 평가 조회 Api 호출
// 수정 시 미리 데이터를 가져오기 위해 사용
export const readEvaluationData = async (token, chatSeq) => {
    try {
        const response = await axios.get(`http://localhost:8089/api/v1/chat/${chatSeq}/evaluation`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    } catch (error) {
        console.error('평가 가져오기 실패', error);
        // 에러 발생 시 반환할 기본 데이터 설정
        return {isEvaluation: false};
    }
};

// 평가 댓글 조회 Api 호출
export const readEvaluationCommentData = async (token, chatSeq) => {
    try {
        const response = await axios.get(`http://localhost:8089/api/v1/chat/${chatSeq}/evaluation/comment`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    } catch (error) {
        console.error('평가 가져오기 실패', error);
    }
};


// 평가 작성 API 호출
export const createEvaluationData = async (token, evaluationData) => {
    try {
        const response = await axios.post('http://localhost:8089/api/v1/evaluation', evaluationData, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
    } catch (error) {
        console.error('평가 작성 실패', error);
        throw error;
    }
};

// 평가 수정 API 호출
export const updateEvaluationData = async (token, evaluationSeq, evaluationData) => {
    try {
        const response = await axios.put(`http://localhost:8089/api/v1/evaluation/${evaluationSeq}`, evaluationData, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });

    } catch (error) {
        console.error('평가 수정 실패', error);
        throw error;
    }
};

// 평가 삭제 API 호출
export const deleteEvaluationData = async (token, chatSeq, evaluationSeq) => {
    try {
        const response = await axios.delete(`http://localhost:8089/api/v1/chat/${chatSeq}/evaluation/${evaluationSeq}`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });

    } catch (error) {
        console.error('평가 삭제 실패', error);
        throw error;
    }
};