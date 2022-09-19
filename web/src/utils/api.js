import axios from "axios";

//
axios.defaults.withCredentials=true
// 响应拦截器
axios.interceptors.response
    .use(response => {
        return response.data;
    }, error => {
        return error.message;
    })

export const getRequest = (url, params) => {
    return axios({
        method: 'get',
        url: `${url}`,
        data: params
    })
}

export const postKeyValueRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${url}`,
        data: params,
        transformRequest: [function (data) {
            // 数据默认会以json格式传递，需要转成key-value
            let ret = '';
            for (let i in data) {
                ret += encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + '&'
            }
            return ret;
        }],
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
    })
}
