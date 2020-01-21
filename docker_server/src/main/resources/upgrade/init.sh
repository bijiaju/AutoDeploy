#!bin/sh
for file in ./*
do
    if test -f $file
    then
        echo $file 是文件
        suffix1=.sh
        if [[  $file == *$suffix1  ]]; then
             echo "我是jpg图片"
            #cp $file $storeDir
			sed -i 's/\r$//' $file
			chmod +x $file
        fi
        #arr=(${arr[*]} $file)
    fi
    if test -d $file
    then
        echo $file 是目录
    fi
done