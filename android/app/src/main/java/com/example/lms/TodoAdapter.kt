package com.example.lms

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.TodoItemBinding

import com.example.lms.Memo
import com.example.lms.dialog.UpdateDialog
import com.example.lms.dialog.UpdateDialogInterface
import com.example.lms.MemoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class TodoAdapter(private val memoViewModel: MemoViewModel) : RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {

    private var memoList = emptyList<Memo>()

    // 뷰 홀더에 데이터를 바인딩
    class MyViewHolder(private val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root),
        UpdateDialogInterface{
        lateinit var memo : Memo
        lateinit var memoViewModel: MemoViewModel

        fun bind(currentMemo : Memo, memoViewModel: MemoViewModel){
            binding.memo = currentMemo
            this.memoViewModel = memoViewModel

            // 체크 리스너 초기화 해줘 중복 오류 방지
            binding.memoCheckBox.setOnCheckedChangeListener(null)

            // 메모 체크 시 체크 데이터 업데이트
            binding.memoCheckBox.setOnCheckedChangeListener { _, check ->
                if (check) {
                    memo = Memo(currentMemo.id, true, currentMemo.content,
                        currentMemo.year, currentMemo.month, currentMemo.day)
                    this.memoViewModel.updateMemo(memo)
                    //this.memoViewModel.deleteMemo(memo)

                }
                else {
                    memo = Memo(currentMemo.id, false, currentMemo.content,
                        currentMemo.year, currentMemo.month, currentMemo.day)
                    this.memoViewModel.updateMemo(memo)
                }
            }

            // 삭제 버튼 클릭 시 메모 삭제
//            binding.deleteButton.setOnClickListener {
//                memoViewModel.deleteMemo(currentMemo)
//            }



            // 수정 버튼 클릭 시 다이얼로그 띄움
            binding.todoLayout.setOnClickListener {
//                memo = currentMemo
//                val myCustomDialog = UpdateDialog(binding.updateButton.context,this)
//                myCustomDialog.show()

                val detailDialog = BottomSheetDialog(binding.todoLayout.context, R.style.AppBottomSheetDialogTheme)
                detailDialog.setContentView(R.layout.todo_detail)
                val todoText = detailDialog.findViewById<TextView>(R.id.todo_detail_View)
                val deleteButton = detailDialog.findViewById<AppCompatButton>(R.id.todoDeleteButton)
                val closeButton = detailDialog.findViewById<AppCompatButton>(R.id.todoCloseButton)
                todoText?.text = currentMemo.content
                //수정
                todoText?.setOnClickListener{
                    memo = currentMemo
                    val myCustomDialog = UpdateDialog(todoText?.context,this)
                    detailDialog.dismiss()
                    myCustomDialog.show()
                }
                //삭제
                deleteButton?.setOnClickListener{
                    detailDialog.dismiss()
                    memoViewModel.deleteMemo(currentMemo)
                }
                //닫기
                closeButton?.setOnClickListener{
                    detailDialog.dismiss()
                }

                detailDialog.show()
            }
        }

        // 다이얼로그의 결과값으로 업데이트 해줌
        override fun onOkButtonClicked(content: String) {
            val updateMemo = Memo(memo.id,memo.check,content,memo.year,memo.month,memo.day)
            memoViewModel.updateMemo(updateMemo)
        }

        override fun onHomeworkOkButtonClicked(content: String) {

        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    // 바인딩 함수로 넘겨줌
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(memoList[position],memoViewModel)
    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount(): Int {
        return memoList.size
    }

    // 메모 리스트 갱신
    fun setData(memo : List<Memo>){
        memoList = memo
        notifyDataSetChanged()
    }

    // 아이템에 아이디를 설정해줌 (깜빡이는 현상방지)
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}