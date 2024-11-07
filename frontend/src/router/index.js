import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    {
        path: "/",
        component: () => import("@/views/main/MainView.vue")
    },
    {
        path: '/signup',
        name: 'Signup',
        component: () => import("@/views/user/SignupView.vue")
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import("@/views/user/LoginView.vue")
    },
    {
        path: '/mypage',
        name: 'Mypage',
        component: () => import("@/views/user/MypageView.vue")
    },
    {
        path: '/counsel',
        name: 'CounselList',
        component: () => import("@/views/counsel/CounselView.vue")
    },
    {
        path: '/chat/:chatSeq/evaluation/',
        name: 'Evaluation',
        component: () => import("@/views/evaluation/EvaluationView.vue")
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// 로그인 안 한 상태에셔 url 에 경로 입력하여 접근하는 동작 막기
// route 이동 전 인증 상태를 확인한다.
// router.beforeEach((to, from, next) => {
//     const authStore = useAuthStore();
//
//     // 인증이 필요한 페이지에 접근할 때
//     if (to.meta.requiresAuth && !authStore.accessToken) {
//         next({path: '/login'}); //  로그인 페이지로 리디렉션
//     }
//     // 로그인 한 후 로그인, 회원가입 페이지에 접근할 때
//     else if (authStore.accessToken && (to.path === '/login' || to.path === '/users')) {
//         next({path: '/users/email'});  // 마이페이지로 리디렉션
//     }
//     else {
//         next();
//     }
// });

export default router;
