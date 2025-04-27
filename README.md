<h1>[補充] 關於JSON格式</h1>
<p>JSON格式是1999年《JavaScript Programming Language, Standard ECMA-262 3rd Edition》的子集合，所以可以在JavaScript以eval()函式（javascript通過eval()呼叫解析器）讀入。不過這並不代表JSON無法使用於其他語言，事實上幾乎所有與網路開發相關的語言都有JSON函式庫。</p>
<p>JSON的基本資料類型：</p>
<ol>
<li>數值：十進制數，不能有前導0，可以為負數，可以有小數部分。還可以用e或者E表示指數部分。不能包含非數，如NaN。不區分整數與浮點數。</li>
<li>字串：以雙引號""括起來的零個或多個Unicode碼位。支援反斜槓開始的跳脫字元序列。</li>
<li>布林值：表示為true或者false。</li>
<li>陣列：有序的零個或者多個值。每個值可以為任意類型。陣列使用方括號[]包裹。多個陣列元素之間用逗號,分隔，形如：[value, value]。</li>
<li>物件：若干無序的「鍵-值對」(key-value pairs)，其中鍵只能是字串[2]。建議但不強制要求對象中的鍵是獨一無二的。對象以花括號{}包裹。多個鍵-值對之間使用逗號,分隔。鍵與值之間用冒號:分隔。</li>
<li>空值：值寫為null</li>
</ol>
<p>舉例：</p>
<pre>
{
     "firstName": "John",
     "lastName": "Smith",
     "sex": "male",
     "age": 25,
     "address": 
     {
         "streetAddress": "21 2nd Street",
         "city": "New York",
         "state": "NY",
         "postalCode": "10021"
     },
     "phoneNumber": 
     [
         {
           "type": "home",
           "number": "212 555-1234"
         },
         {
           "type": "fax",
           "number": "646 555-4567"
         }
     ]
 }
</pre>
<p>資料來源：https://zh.wikipedia.org/zh-tw/JSON</p>
