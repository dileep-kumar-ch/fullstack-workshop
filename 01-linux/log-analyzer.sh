
name="/c/Users/dilee/Desktop/Revature/excersices/devtraining/sample-log.txt"

if [ -f "$name" ]
then
   echo "exists" 
fi


echo "the file contains $(wc -l <"$name")lines"
echo "info : $(grep -i -c "info" "$name")"
echo "warning : $(grep -i -c "warning" "$name")"
echo "error : $(grep -i -c "error" "$name")"
echo "uniq ip adress found IP_LIST=$(grep -oE '[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}' "$name" | sort | uniq)"


