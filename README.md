ztask2
======

一个关于 zTask 的临时试验性的修改

控件:
                   |> ztask.usr.projects
                   |> ztask.usr.messager
[OK]tabs.h         |> usr.password [OK]
		ztask.usr  -> usr.profile [..]
				   |
		ztask.proj -> avatas [OK]
		           |> ztask.proj.general
		           |> ztask.proj.rename
		           |> ztask.proj.privilege
		           |> ztask.proj.member
		           |> ztask.proj.messager
		           |> ztask.proj.stacks
	
    ztask.proj.new
    ztask.proj.show

	
	tree
	ztask.stack   |> ztask.stack.filter
                  |> ztask.stack.bar
	              |> ztask.stack.tree		
	
	bubblebar
	scroller
	ztask.task    |> ztask.task.filter
	              |> ztask.task.bar
	              |> ztask.task.list
	
函数
	$z.ztask.draw     # 在任何选区都可以根据 t 对象建立一个 task
    $z.be.pop
    $z.be.masker

jQuery 插件:
	droplist
    [OK] password
