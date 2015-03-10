from HTMLParser import HTMLParser

students = [];
# create a subclass and override the handler methods
class MyHTMLParser(HTMLParser):
    def __init__(self):
        HTMLParser.__init__(self)
        self.recording = 0
        self.data = []
        
    def handle_starttag(self, tag, attributes):
        if tag == 'font':
            #print attributes[0][1]
            if attributes[0][1] == '#181818':
                self.recording = 1
    
    def handle_endtag(self, tag):
        if tag == 'font':
            self.recording = 0
        
    def handle_data(self, data):
        if self.recording:
            #print data
            students.append(data)

# instantiate the parser and fed it some HTML
parser = MyHTMLParser()
f = open('1.txt','r')
text = f.readlines()[0];
#print text
parser.feed(text)
students = students[::2]

f = open('students.txt','w')
for item in students:
      f.write("%s\n" % item)
