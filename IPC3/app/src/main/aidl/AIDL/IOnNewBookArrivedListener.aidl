// IOnNewBookArrivedListener.aidl
package AIDL;

import AIDL.Book;

interface IOnNewBookArrivedListener {
   void OnNewBookArrived(in Book book);
}
