import re
import ast
import operator


class Cell:
    exp = ""
    value = 0

    def __init__(self, sheet):
        self.sheet = sheet

    def update(self):
        refs = self.sheet.getrefs(self)
        D = {}
        for ref in refs:
            D[ref] = self.sheet.cell(ref).value
        if self.exp != "":
            res = self.eval_expression(self.exp, D)
            self.value = res

    def eval_expression(self, exp, variables={}):
        def _eval(node):
            if isinstance(node, ast.Num):
                return node.n
            elif isinstance(node, ast.Name):
                return variables[node.id]
            elif isinstance(node, ast.BinOp):
                binOps = {
                    ast.Add: operator.add,
                    ast.Sub: operator.sub,
                    ast.Mult: operator.mul,
                    ast.Div: operator.truediv,
                    ast.Mod: operator.mod
                }
                return binOps[type(node.op)](_eval(node.left), _eval(node.right))
            else:
                raise Exception('Unsupported type {}'.format(node))

        node = ast.parse(exp, mode='eval')
        return _eval(node.body)


class Sheet:
    cells = []

    def __init__(self, y, x):
        self.x = x
        self.y = y
        for i in range(0, y):
            self.cells.append([])
            for j in range(0, x):
                self.cells[i].append(Cell(self))

    def cell(self, ref):
        return self.cells[int(ref[1:]) - 1][ord(ref[0]) - 65]

    def set(self, ref, content):
        cell = self.cell(ref)
        old = cell.exp
        cell.exp = content
        newRefs = self.getrefs(cell)
        for newRef in newRefs:
            if ref in self.getrefs(self.cell(newRef)):
                cell.exp = old
                raise RuntimeError("Circular reference")
        self.notify()
        return True

    def getrefs(self, cell):
        refs = re.findall("[A-Z]+[1-9][0-9]*", cell.exp)
        return refs

    def print(self):
        row = ""
        for j in range(0, self.x):
            row += 10 * "-"
        row += "-"
        print(row)
        for i in range(0, self.y):
            row = ""
            for j in range(0, self.x):
                row += "{:<10}".format("|" + str(self.cells[i][j].value))
            row += "|"
            print(row)
            row = ""
            for j in range(0, self.x):
                row += 10 * "-"
            row += "-"
            print(row)

    def notify(self):
        for row in self.cells:
            for cell in row:
                cell.update()


if __name__ == "__main__":
    s = Sheet(5, 5)
    print()

    s.set('A1', '2')
    s.set('A2', '5')
    s.set('A3', 'A1+A2')
    s.print()
    print()

    s.set('A1', '4')
    s.set('A4', 'A1+A3')
    s.print()
    print()

    try:
        s.set('A1', 'A3')
    except RuntimeError as e:
        print("Caught exception:", e)
    s.print()
    print()
