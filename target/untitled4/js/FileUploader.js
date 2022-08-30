const TASK_STATUS = {
    PROCESSING: 1,
    SUCCESS: 2,
    ERROR: 3,
}



// 先获取地址栏中信息,了解需要查看的是哪种分类，因为前面get方式获取时后面拼接了这些参数
let str = decodeURIComponent(window.location.search);

// 去掉问号
str = str.substr(1);

const arr = str.split('=');

// 每页显示数据数量
let lineNum = 16;

// 第一次调用
getAjax(1);

// 发送ajax请求从后端获取数据,第一次显示第一页的内容,
// 设定函数用来发送ajax请求,因为要多次请求,所以定义成函数
// 参数是请求的页面,就是一页显示多少就请求多少数据
function getAjax(page) {

    $.ajax({
        url: '../php/goods_list.php',

        // 传入后端的参数
        data: {
            cat_one_id: arr[1], // 利用id分类找数据
            page: page,  // 当前页数
            line: lineNum,  // 每页显示的数据数量
        },

        type: 'get',

        dataType: 'json',

        // 接收到响应体后执行的程序
        success: function (res) {
            // console.log(res);

            let str = "";

            res.forEach(function (item) {

                str += `<li class="list-item">
                <div class="panel panel-primary">
                    <div class="panel-body">
                        <ol class="breadcrumb">
                            <li><a href="#">${item.cat_one_id}</a></li>
                            <li><a href="#">${item.cat_two_id}</a></li>
                            <li class="active">${item.cat_three_id}</li>
                        </ol>
                    </div>
                    <div class="panel-footer">
                        <div class="row">
                            <div>
                                <div class="thumbnail">
                                    <img src="${item.goods_big_logo}"
                                        alt="...">
                                    <div class="caption">
                                        <h3>${item.goods_name}</h3>
                                        <p>
                                            <i class="glyphicon glyphicon-yen"></i>
                                            <span>${item.goods_price}</span>
                                        </p>
                                        <p><a href="javascript:;" class="btn btn-primary" role="button">查找相似商品</a> <a
                                                href="../pagess/detail.html?goods_id=${item.goods_id}" class="btn btn-danger" role="button">查看商品详情</a></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>`;

            })

            $('ul').html(str);

            // 数据太多要先分页显示,这里利用插件分页,设定分页样式
            // 根据查询结果进行分页显示
            // 一页至少有一个res[0]数据,存储的有页面信息(当前页数,总共数据数量,)
            $('.page').pagination({

                mode: 'fixed',
                pageCount: res[0].sumPage,
                totalData: res[0].row,
                current: res[0].page,
                showData: lineNum, // 每页显示的数量
                activeCls: 'active', // 给点击的页数按钮添加样式，就是个类名
                coping: true,
                homePage: '首页',
                endPage: '末页',
                prevContent: '上页',
                nextContent: '下页',

                // 插件定义的函数
                callback: function (res) {

                    // 获取点击分页的当前页数
                    let p = res.getCurrent();

                    // 上面得到的当前页就是请求函数的参数,继续发送请求
                    getAjax(p);

                }
            });

        }

    });
}






class FileUploader {
    constructor({
                    element,
                    uploadUrl,
                    taskRenderer
                }) {
        if (element instanceof HTMLElement) {
            this.element = element;
        } else {
            throw new Error('element should be an HTMLElement');
        }
        this.uploadUrl = uploadUrl;
        this.taskRenderer = taskRenderer;
        this.#init();
    }

    // public props
    tasks = [];

    // private methods
    #init = () => {
        this.#listenToEvents();
    }

    #listenToEvents = () => {
        const dropAreaDOM = this.element.querySelector('.drop-area');
        dropAreaDOM.addEventListener('drop', this.#handleDrop);
        dropAreaDOM.addEventListener('dragover', this.#handleDragover);
    }

    #handleDrop = (e) => {
        // Prevent file from being opened
        e.preventDefault();

        if (e.dataTransfer.items) {
            // Use DataTransferItemList interface to access files
            for (const item of e.dataTransfer.items) {
                if (item.kind === 'file') {
                    const file = item.getAsFile();
                    console.log('file: ', file);
                    this.#upload(file);
                }
            }
        } else {
            // Use DataTransfer interface to access the files
            for (const file of e.dataTransfer.files) {
                console.log('file: ', file);
                this.#upload(file);
            }
        }
    }

    #handleDragover = (e) => {
        // Prevent file from being opened
        e.preventDefault();
    }

    #upload = (file) => {
        const data = new FormData();
        data.append('file', file);
        const task = {
            id: this.tasks.length,
            name: file.name,
            status: TASK_STATUS.PROCESSING,
            progress: 0
        }
        this.tasks.unshift(task);
        const xhr = new XMLHttpRequest();
        xhr.open('POST', this.uploadUrl);
        xhr.setRequestHeader('x-file-name', encodeURIComponent(file.name));
        xhr.upload.addEventListener('progress', (e) => {
            const { loaded, total } = e;
            const progress = Math.round(loaded / total * 100);
            task.progress = progress;
            this.#updateTask(task);
        });
        xhr.addEventListener('load', (e) => {
            task.status = TASK_STATUS.SUCCESS;
            const response = JSON.parse(xhr.response);
            console.log('response', response);
            const { url } = response;
            task.url = url;
            this.#updateTask(task);
        });
        xhr.addEventListener('error', (e) => {
            task.status = TASK_STATUS.ERROR;
            this.#updateTask(task);
        });
        xhr.send(data);
    }

    #updateTask = (task) => {
        const taskList = this.element.querySelector('.task-list');
        const id = `task-${task.id}`;
        let taskBox = taskList.querySelector(`#${id}`);
        if (!taskBox) {
            taskBox = document.createElement('div');
            taskBox.id = id;
            taskList.prepend(taskBox);
        }
        taskBox.innerHTML = '';
        taskBox.append(this.taskRenderer(task));
    }
}