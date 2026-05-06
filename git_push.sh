#!/bin/bash

# 检查是否提供了提交信息
if [ -z "$1" ]; then
  echo "请提供提交信息。用法: ./git_commit_push.sh '提交信息'"
  exit 1
fi

# 拉取远程仓库的最新更改
git pull

# 添加所有更改的文件
git add .

# 提交更改
git commit -m "$1"

# 推送到远程仓库
git push

echo "提交和推送完成！"