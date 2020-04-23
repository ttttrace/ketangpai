import Vue from 'vue'
import Router from 'vue-router'
import index from '@/view/index'
import login from '@/view/login'
import register from '@/view/register'
import course from '@/view/course'
import courseIndex from '@/view/courseIndex'
import headerNav from '@/view/headerNav'
import userForm from '@/view/userForm'
import workNav from '@/view/workNav'
import submitWork from "@/view/submitWork"
import workInfo from '@/view/workInfo'
import StuWork from '@/view/StuWork'
import preview from '@/view/preview'
Vue.use(Router)
const VueRouterPush = Router.prototype.push
Router.prototype.push = function push(to) {
    return VueRouterPush.call(this, to).catch(err => err)
}
export default new Router({
    routes: [
        {
            path:"/pre/:workId/:id/:file",
            name:"preview",
            component:preview,
        },
        {
            path: "/",
            redirect: "/index"
        },
        {
            path: '/index',
            name: 'index',
            component: index,
        },
        {
            path: '/login',
            name: 'login',
            component: login,
        },
        {
            path: '/register',
            name: 'register',
            component: register,
        },
        {
            path: '/course/courseIndex/:courseId/:role',
            name: 'courseIndex',
            component: courseIndex,
        },
        {
            path:'/headerNav',
            name: 'headerNav',
            redirect:'/course',
            component: headerNav,
            children:[{
                path: '/course',
                name: 'course',
                component: course,
            },
            {
                path: '/userInfo',
                name: 'userForm',
                component: userForm,
            }]
        },
        {
            path:'/workNav',
            name:'workNav',
            component:workNav,
            redirect:"/sub/:courseId/:workId",
            children:[
                {
                    path:"/sub/:courseId/:workId/:role",
                    name:"submitWork",
                    component:submitWork
                },
                {
                    path:"/workInfo/:courseId/:workId/:role",
                    name:"workInfo",
                    component:workInfo
                },
                {
                    path:"/stuWork/:courseId/:workId/:role/:filter",
                    name:"StuWork",
                    component:StuWork,
                },
            ]
        }
    ]
})
