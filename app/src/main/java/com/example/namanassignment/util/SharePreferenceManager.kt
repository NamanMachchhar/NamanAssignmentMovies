package com.example.namanassignment.util

import android.content.Context

class SharePreferenceManager(var mContext: Context) {

    companion object {
        private var mSharePreferenceInstance: SharePreferenceManager? = null
        const val COMMENT_PREF = "comment_pref"

        @Synchronized
        fun getInstance(mContext: Context): SharePreferenceManager? {
            if (mSharePreferenceInstance == null) {
                mSharePreferenceInstance = SharePreferenceManager(mContext)
            }
            return mSharePreferenceInstance
        }
    }

    // Save Comment
    fun saveComment(comment: String?) {
        val mSharePreference = mContext.getSharedPreferences(COMMENT_PREF, Context.MODE_PRIVATE)
        val mEditor = mSharePreference.edit()
        mEditor.putString(AppConstants.COMMENT, comment!!)
        mEditor.apply()
    }
    fun getComment(): String? {
        val mSharedPreferences = mContext.getSharedPreferences(COMMENT_PREF, Context.MODE_PRIVATE)
        return mSharedPreferences.getString(AppConstants.COMMENT,"")
    }

}
